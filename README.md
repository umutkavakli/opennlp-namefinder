# NameFinder

A machine learning application with pre-trained models that finds names on websites with given URLs as input. 
<hr>

## [Models](src/main/resources/models) 

- Sentence Tokenizer: "en-sent.bin"
<br> Takes documents and breaks them into sentences. Therefore, the output is array of sentences.
<br>

- Word Tokenizer: "en-token.bin"
<br> Takes sentences (which comes from sentence tokenizer) and breaks them into words. Therefore, the output is array of words for every sentence.
<br>  

- NameFinder: "en-ner-person.bin"
<br> Takes words (which comes from word tokenizer) and checks them whether or not it's english name or not. If there is a english name it returns a span where the location of name is in word array.
<hr>

## Usage

Clone repository:
```
git clone https://github.com/umutkavakli/opennlp-namefinder.git
```
Move to project directory:
```
cd opennlp-namefinder/
```
Clean, compile and package source code in JAR format :
```
mvn clean package
```
Run format:
```
java -jar target/namefinder.jar <URL>
```

## Example

```
java -jar target/namefinder.jar https://opennlp.apache.org/books-tutorials-and-talks.html
```

<b>Output:</b>
<br>Berlin Buzzwords
<br>Boris Galitsky
<br>Drew Farris
<br>Peter Thygesen
<br>Tom Morton

## Reference
[opennlp](https://opennlp.apache.org/docs/2.1.0/manual/opennlp.html)