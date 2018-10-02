package com.example.rest.client;

import com.example.rest.dto.ApiResponse;
import com.example.rest.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class RestClient {
    private static final Logger LOG = (Logger) LoggerFactory.getLogger(RestClient.class);

    private RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders headers;

    @PostConstruct
    public void setHeaders() {
        headers = new HttpHeaders() {
            {
                setContentType(MediaType.APPLICATION_JSON);
            }
        };
    }

    public <T> ResponseEntity<ApiResponse<T>> post(String url, Object request, HttpHeaders httpHeaders) {
        HttpHeaders requestHeaders = getHeaders(httpHeaders);
        final HttpEntity<Object> requestEntity = new HttpEntity<>(request, requestHeaders);
        LOG.info("Request url: {} headers: {} body: {}", url, requestHeaders, JsonUtils.toJsonString(request));
        ResponseEntity<ApiResponse<T>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return log(responseEntity);
    }

    public <T> ResponseEntity<ApiResponse<T>> post(String url, Object request) {
        return post(url, request, headers);
    }

    public <T> ResponseEntity<ApiResponse<T>> patch(String url, Object request, HttpHeaders httpHeaders) {
        HttpHeaders requestHeaders = getHeaders(httpHeaders);
        final HttpEntity<Object> requestEntity = new HttpEntity<>(request, requestHeaders);
        LOG.info("Request url: {} headers: {} body: {}", url, requestHeaders, JsonUtils.toJsonString(request));
        ResponseEntity<ApiResponse<T>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return log(responseEntity);
    }

    public <T> ResponseEntity<ApiResponse<T>> patch(String url, Object request) {
        return patch(url, request, headers);
    }

    public <T> ResponseEntity<ApiResponse<T>> put(String url, Object request, HttpHeaders httpHeaders) {
        HttpHeaders requestHeaders = getHeaders(httpHeaders);
        final HttpEntity<Object> requestEntity = new HttpEntity<>(request, requestHeaders);
        LOG.info("Request url: {} headers: {} body: {}", url, requestHeaders, JsonUtils.toJsonString(request));
        ResponseEntity<ApiResponse<T>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return log(responseEntity);
    }

    public <T> ResponseEntity<ApiResponse<T>> put(String url, Object request) {
        return put(url, request, headers);
    }

    public <T> ResponseEntity<ApiResponse<T>> delete(String url) {
        final HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        LOG.info("Request url: {} headers: {}", url, headers);
        ResponseEntity<ApiResponse<T>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return log(responseEntity);
    }

    public <T> ResponseEntity<ApiResponse<T>> get(String url) {
        final HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        LOG.info("Request url: {} headers: {}", url, headers);
        ResponseEntity<ApiResponse<T>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<ApiResponse<T>>() {}
        );
        return log(responseEntity);
    }

    private HttpHeaders getHeaders(HttpHeaders httpHeaders) {
        if (httpHeaders == null) {
            httpHeaders.putAll(headers);
        } else {
            return httpHeaders;
        }
        return httpHeaders;
    }

    private <T> ResponseEntity<ApiResponse<T>> log(ResponseEntity<ApiResponse<T>> responseEntity) {
        LOG.info("Response status: {} headers: {} body: {}",
                responseEntity.getStatusCode(),
                responseEntity.getHeaders(),
                JsonUtils.toJsonString(responseEntity.getBody())
        );
        return responseEntity;
    }
}
