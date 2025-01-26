<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <title>N-Gram Chatbot</title>
    <link
      rel="stylesheet"
      type="text/css"
      href="resources/assets/css/style.css"
    />
  </head>
  <body>
    <div class="container">
      <h2>N-Gram Chatbot</h2>
      <form action="chatbot" method="post" enctype="multipart/form-data">
        <label for="inputSentence">Enter Sentence:</label>
        <input
          type="text"
          id="inputSentence"
          name="inputSentence"
          required
        /><br /><br />

        <label for="nSlider">Select N-Gram size:</label>
        <input
          type="range"
          id="nSlider"
          name="nSlider"
          min="1"
          max="10"
          value="2"
        /><br /><br />

        <label for="inputNumWords">Number of Words to Predict:</label>
        <input
          type="number"
          id="inputNumWords"
          name="inputNumWords"
          value="3"
        /><br /><br />

        <input type="radio" id="predict" name="action" value="predict" />
        <label for="predict">Predict Next Words</label><br />

        <input type="radio" id="perplexity" name="action" value="perplexity" />
        <label for="perplexity">Calculate Perplexity</label><br /><br />

        <input type="submit" value="Submit" />
      </form>
    </div>
  </body>
</html>
