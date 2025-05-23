package com.oasis.oasisaiagent.demo.invoke;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpAiInvoke {

    public static void main(String[] args) throws IOException {
        String apiKey = TestApiKey.API_KEY;
        if (apiKey == null) {
            System.out.println("API Key 未设置。请确保环境变量 'DASHSCOPE_API_KEY' 已设置。");
            return;
        }

        String url = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
        String jsonContent = "{"
                + "\"model\": \"qwen-plus\","
                + "\"messages\": [{"
                + "    \"role\": \"system\","
                + "    \"content\": \"You are a helpful assistant.\""
                + "}, {"
                + "    \"role\": \"user\","
                + "    \"content\": \"你是谁？\""
                + "}]}";

        String result = sendPostRequest(url, jsonContent, apiKey);
        System.out.println(result);
    }

    private static String sendPostRequest(String url, String jsonContent, String apiKey) {
        HttpURLConnection connection = null;
        try {
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonContent.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            String responseBody = readResponse(connection, responseCode);

            if (responseCode >= 200 && responseCode < 300) {
                return responseBody;
            } else {
                return "请求失败: " + responseCode + " - " + responseBody;
            }
        } catch (Exception e) {
            return "请求异常: " + e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String readResponse(HttpURLConnection connection, int responseCode) throws IOException {
        InputStream inputStream = responseCode < 400 ?
                connection.getInputStream() :
                connection.getErrorStream();

        if (inputStream == null) {
            return "";
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }
}