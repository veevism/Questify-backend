package com.backend.questify.Service;

import com.backend.questify.DTO.User.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {

	private final WebClient webClient;

	@Autowired
	public ExternalApiService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://misapi.cmu.ac.th").build();
	}

	public Mono<UserRequestDto> getUserInfo(String tokenA) {
		return webClient.get()
						.uri("/cmuitaccount/v1/api/cmuitaccount/basicinfo")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenA)
						.accept(MediaType.APPLICATION_JSON)
						.retrieve()
						.bodyToMono(UserRequestDto.class)
						.onErrorMap(WebClientResponseException.class, e ->
								new RuntimeException("Error calling CMU external API: " + e.getStatusCode(), e));
	}
}