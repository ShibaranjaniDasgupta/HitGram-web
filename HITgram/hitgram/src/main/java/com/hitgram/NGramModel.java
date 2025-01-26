package com.hitgram;

import java.io.*;
import java.util.*;

public class NGramModel {
    private Map<String, Map<String, Integer>> nGramModel = new HashMap<>();
    private Map<String, Integer> tokenFrequencyMap = new HashMap<>();
    private static final int VOCAB_SIZE = 10000; // Estimate of vocabulary size for smoothing

    // Method to build N-Gram model from a given text
    public void buildModel(String corpus, int n) {
        nGramModel.clear();
        tokenFrequencyMap.clear();

        String[] tokens = corpus.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        for (int i = 0; i <= tokens.length - n; i++) {
            StringBuilder nGramBuilder = new StringBuilder();
            for (int j = 0; j < n - 1; j++) {
                nGramBuilder.append(tokens[i + j]).append(" ");
            }
            String nGramPrefix = nGramBuilder.toString().trim();
            String nextToken = tokens[i + n - 1];

            tokenFrequencyMap.put(nextToken, tokenFrequencyMap.getOrDefault(nextToken, 0) + 1);

            nGramModel.putIfAbsent(nGramPrefix, new HashMap<>());
            Map<String, Integer> suffixMap = nGramModel.get(nGramPrefix);
            suffixMap.put(nextToken, suffixMap.getOrDefault(nextToken, 0) + 1);
        }
    }

    // Method to predict the next word based on N-Gram model
    public String predictNextWord(String inputSentence, int n, int numWordsToPredict) {
        String[] inputTokens = inputSentence.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        StringBuilder result = new StringBuilder();
        List<String> context = new ArrayList<>(Arrays.asList(inputTokens));

        for (int i = 0; i < numWordsToPredict; i++) {
            StringBuilder nGramBuilder = new StringBuilder();
            for (int j = Math.max(0, context.size() - (n - 1)); j < context.size(); j++) {
                nGramBuilder.append(context.get(j)).append(" ");
            }
            String nGramPrefix = nGramBuilder.toString().trim();
            String predictedWord = predictNextWordForNGram(nGramPrefix);
            context.add(predictedWord);
            result.append(predictedWord).append(" ");
        }
        return result.toString().trim();
    }

    // Helper method to predict the next word for a given N-Gram prefix
    private String predictNextWordForNGram(String nGramPrefix) {
        Map<String, Integer> suffixMap = nGramModel.getOrDefault(nGramPrefix, new HashMap<>());
        int totalSuffixCount = suffixMap.values().stream().mapToInt(Integer::intValue).sum();
        double maxProbability = -1.0;
        String predictedWord = null;

        for (Map.Entry<String, Integer> entry : suffixMap.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            double probability = (count + 1.0) / (totalSuffixCount + VOCAB_SIZE); // Laplace smoothing
            if (probability > maxProbability) {
                maxProbability = probability;
                predictedWord = word;
            }
        }
        if (predictedWord == null) {
            predictedWord = "unknown"; // Handle case where no suitable word is found
        }
        return predictedWord;
    }

    // Method to calculate the probability of a sentence
    public double calculateProbability(String sentence, int n) {
        String[] tokens = sentence.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        double probability = 1.0;

        for (int i = 0; i <= tokens.length - n; i++) {
            StringBuilder nGramBuilder = new StringBuilder();
            for (int j = 0; j < n - 1; j++) {
                nGramBuilder.append(tokens[i + j]).append(" ");
            }
            String nGramPrefix = nGramBuilder.toString().trim();
            String nextToken = tokens[i + n - 1];
            Map<String, Integer> suffixMap = nGramModel.getOrDefault(nGramPrefix, new HashMap<>());
            int count = suffixMap.getOrDefault(nextToken, 0);
            int totalSuffixCount = suffixMap.values().stream().mapToInt(Integer::intValue).sum();
            probability *= (count + 1.0) / (totalSuffixCount + VOCAB_SIZE); // Laplace smoothing
        }
        return probability;
    }

    // Method to calculate perplexity of a sentence
    public double calculatePerplexity(String sentence, int n) {
        double probability = calculateProbability(sentence, n);
        int numTokens = sentence.split("\\s+").length;
        return Math.pow(1.0 / probability, 1.0 / numTokens);
    }
}
