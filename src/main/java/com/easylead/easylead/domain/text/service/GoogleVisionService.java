package com.easylead.easylead.domain.text.service;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.util.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleVisionService {
    public String detechStringImage(String url){
        StopWatch totalTime = new StopWatch();
        totalTime.start();

        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(url).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            StringBuilder result = new StringBuilder();
            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return null;
                }

                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    result.append(annotation.getDescription()).append(" ");
                }
            }

            totalTime.stop();
            System.out.println("Total Time : " + totalTime.getTotalTimeMillis() + "ms");

            return result.toString();
        }
        catch (Exception exception) {
            return exception.getMessage();
        }
    }
    public String detectDocumentsGcs(String gcsSourcePath, String gcsDestinationPath)
            throws Exception {

        StringBuilder fullText = new StringBuilder();


        AnnotateImageResponse annotateImageResponse = null;
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            List<AsyncAnnotateFileRequest> requests = new ArrayList<>();

            GcsSource gcsSource = GcsSource.newBuilder().setUri(gcsSourcePath).build();

            InputConfig inputConfig =
                    InputConfig.newBuilder()
                            .setMimeType(
                                    "application/pdf") // Supported MimeTypes: "application/pdf", "image/tiff"
                            .setGcsSource(gcsSource)
                            .build();

            GcsDestination gcsDestination =
                    GcsDestination.newBuilder().setUri(gcsDestinationPath).build();

            OutputConfig outputConfig =
                    OutputConfig.newBuilder().setBatchSize(2).setGcsDestination(gcsDestination).build();

            // Select the Feature required by the vision API
            Feature feature = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();

            // Build the OCR request
            AsyncAnnotateFileRequest request =
                    AsyncAnnotateFileRequest.newBuilder()
                            .addFeatures(feature)
                            .setInputConfig(inputConfig)
                            .setOutputConfig(outputConfig)
                            .build();

            requests.add(request);

            // Perform the OCR request
            OperationFuture<AsyncBatchAnnotateFilesResponse, OperationMetadata> response =
                    client.asyncBatchAnnotateFilesAsync(requests);

            System.out.println("Waiting for the operation to finish.");

            // Wait for the request to finish. (The result is not used, since the API saves the result to
            // the specified location on GCS.)
            List<AsyncAnnotateFileResponse> result =
                    response.get(180, TimeUnit.SECONDS).getResponsesList();

            // Once the request has completed and the System.output has been
            // written to GCS, we can list all the System.output files.
            Storage storage = StorageOptions.getDefaultInstance().getService();

            // Get the destination location from the gcsDestinationPath
            Pattern pattern = Pattern.compile("gs://([^/]+)/(.+)");
            Matcher matcher = pattern.matcher(gcsDestinationPath);

            if (matcher.find()) {
                String bucketName = matcher.group(1);
                String prefix = matcher.group(2);

                // GCS 버킷에서 주어진 접두어로 객체 목록 가져오기
                Bucket bucket = storage.get(bucketName);
                com.google.api.gax.paging.Page<Blob> pageList = bucket.list(Storage.BlobListOption.prefix(prefix));

                List<Blob> blobList = new ArrayList<>();
                pageList.iterateAll().forEach(blobList::add);

                // 파일 이름에서 숫자를 추출하여 정렬하기
                blobList.sort(Comparator.comparingInt(blob -> extractPageNumber(blob.getName())));

                // 정렬된 파일 목록을 출력
                System.out.println("출력 파일들:");
                for (Blob blob : blobList) {
                    System.out.println(blob.getName());

                    String jsonContents = new String(blob.getContent());
                    AnnotateFileResponse.Builder builder = AnnotateFileResponse.newBuilder();
                    JsonFormat.parser().merge(jsonContents, builder);

                    AnnotateFileResponse annotateFileResponse = builder.build();
                    for (AnnotateImageResponse a : annotateFileResponse.getResponsesList()) {
                        fullText.append(a.getFullTextAnnotation().getText());
                    }

                }


            } else {
                System.out.println("No MATCH");
            }
        }
        return fullText.toString();
    }
    private int extractPageNumber(String fileName) {
        Pattern pattern = Pattern.compile("output-(\\d+)-to-\\d+\\.json");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return Integer.MAX_VALUE; // 패턴이 일치하지 않을 경우 높은 값 반환
    }

}
