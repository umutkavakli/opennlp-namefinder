package namefinder;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {

        try {
            Document doc = Jsoup.connect(args[0]).get();
            String text = doc.body().text();
            findNames(text);
        } catch (Exception exception) {
            System.out.println("An error occurred when taking url input: " + exception);
        }
    }

    public static void findNames(String text) {
        // Create string path to directory of all models
        String modelsPath = "src/main/resources/models/";

        // Gather all paths of models: Sentences ---> Words ---> Names
        Path sentenceTokenizerPath = Paths.get(modelsPath + "en-sent.bin");
        Path tokenizerPath = Paths.get(modelsPath + "en-token.bin");
        Path nameFinderPath = Paths.get(modelsPath + "en-ner-person.bin");

        try {
            // Loading models putting paths as input
            SentenceModel sentenceModel = new SentenceModel(sentenceTokenizerPath);
            TokenizerModel tokenizerModel = new TokenizerModel(tokenizerPath);
            TokenNameFinderModel nameFinderModel = new TokenNameFinderModel(nameFinderPath);

            // Creating "Sentence Detector" using loaded model
            SentenceDetector sentenceDetector = new SentenceDetectorME(sentenceModel);
            Tokenizer tokenizer = new TokenizerME(tokenizerModel);
            NameFinderME nameFinder = new NameFinderME(nameFinderModel);

            // Storing all sentences in an array with sentence detector
            String[] sentences = sentenceDetector.sentDetect(text);

            TreeSet<String> names = new TreeSet<>();

            for (int i = 0; i < sentences.length; i++) {

                // Storing all tokens in the sentence[i]
                String[] tokens = tokenizer.tokenize(sentences[i]);

                // Storing all founded names location of the tokens
                Span[] nameSpans = nameFinder.find(tokens);

                // Converting founded names to a visual string array
                String[] namesInSentence = Span.spansToStrings(nameSpans, tokens);

                names.addAll(Arrays.asList(namesInSentence));

            }

            for (String s:names) {
                System.out.println(s);
            }

        } catch (Exception exception) {
            System.out.println("An error occurred: " + exception);
        }
    }
}
