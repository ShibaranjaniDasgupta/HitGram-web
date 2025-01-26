package com.hitgram;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class NGramModel {

    private Map<String, Map<String, Integer>> nGramModel;

    public NGramModel() {
        this.nGramModel = new HashMap<>();
    }

    // Build the N-Gram model from file
    public void buildModel(String filePath) throws IOException {
        String content = FileUtils.readFileToString(new File(filePath), "UTF-8");
        String[] words = content.toLowerCase().split("\\s+");

        for (int i = 0; i < words.length - 1; i++) {
            String key = words[i];
            String nextWord = words[i + 1];

            nGramModel.putIfAbsent(key, new HashMap<>());
            Map<String, Integer> nextWords = nGramModel.get(key);
            nextWords.put(nextWord, nextWords.getOrDefault(nextWord, 0) + 1);
        }
    }

    // Predict the next words based on input
    public List<String> predictText(String sentence, int numWords) {
        String[] words = sentence.toLowerCase().split("\\s+");
        String lastWord = words[words.length - 1];

        List<String> result = new ArrayList<>();

        for (int i = 0; i < numWords; i++) {
            Map<String, Integer> nextWords = nGramModel.get(lastWord);
            if (nextWords == null || nextWords.isEmpty()) {
                break;
            }

            String nextWord = Collections.max(nextWords.entrySet(), Map.Entry.comparingByValue()).getKey();
            result.add(nextWord);
            lastWord = nextWord;
        }

        return result;
    }
}
