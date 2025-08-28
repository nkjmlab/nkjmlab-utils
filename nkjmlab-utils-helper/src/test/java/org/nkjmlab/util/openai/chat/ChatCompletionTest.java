package org.nkjmlab.util.openai.chat;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;

import org.nkjmlab.util.openai.chat.model.ChatResponse;

class ChatCompletionTest {

	void test() {
		ChatResponse res = ChatCompletion.builder()
				.apiKeyFromProperties(
						Paths.get(System.getProperty("user.home"), ".openai", "openai4j.properties").toString())
				.build().createCompletion("What do you think would be good for dinner tonight?");
		assertThat(res.choices().size()).isNotEqualTo(0);
	}
}
