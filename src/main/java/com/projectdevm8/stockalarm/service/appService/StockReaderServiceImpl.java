package com.projectdevm8.stockalarm.service.appService;

import com.projectdevm8.stockalarm.ConfigEx.config.AlphavantageConfig;
import com.projectdevm8.stockalarm.ConfigEx.exception.StockReaderException;
import com.projectdevm8.stockalarm.service.implementation.StockReaderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class StockReaderServiceImpl implements StockReaderService {

  private static final String ROOT_NAME = "Global Quote";
  private static final String PRICE_NODE = "05. price";

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;
  private final AlphavantageConfig config;

  public StockReaderServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper, AlphavantageConfig config) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
    this.config = config;
  }

  @Override
  public Double getPrice(String symbol) {
    try {
      String json = Objects.requireNonNull(restTemplate.getForObject(config.getUrlForPrice(symbol), String.class));
      JsonNode root = objectMapper.readTree(json);
      return root.path(ROOT_NAME).path(PRICE_NODE).require().asDouble();
    } catch (JsonProcessingException e) {
      throw new StockReaderException(String.format("Nu pot citi pretul pentru simbolul=%s", symbol), e);
    } catch (IllegalArgumentException e) {
      throw new StockReaderException(String.format("Stock-ul cu simbolul =%s nu exista", symbol), e);
    } catch (RestClientException | NullPointerException e) {
      throw new StockReaderException("Sursa invalida", e);
    }
  }
}
