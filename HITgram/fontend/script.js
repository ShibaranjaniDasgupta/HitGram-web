// Function to handle the file upload and model building
document.getElementById('upload-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const fileInput = document.getElementById('file-input');
    const statusDiv = document.getElementById('upload-status');
    const file = fileInput.files[0];

    if (!file) {
        statusDiv.textContent = 'Please select a file.';
        statusDiv.style.color = 'red';
        return;
    }

    const formData = new FormData();
    formData.append('file', file);

    fetch('http://localhost:8080/upload', {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        statusDiv.textContent = data;
        statusDiv.style.color = 'green';
    })
    .catch(error => {
        statusDiv.textContent = 'Error uploading file: ' + error.message;
        statusDiv.style.color = 'red';
    });
});

// Function to handle text prediction
document.getElementById('prediction-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const sentenceInput = document.getElementById('sentence-input').value;
    const numWordsInput = document.getElementById('num-words-input').value;
    const predictionResultDiv = document.getElementById('prediction-result');

    if (!sentenceInput.trim()) {
        predictionResultDiv.textContent = 'Please enter a sentence.';
        predictionResultDiv.style.color = 'red';
        return;
    }

    fetch(`http://localhost:8080/predict?sentence=${encodeURIComponent(sentenceInput)}&numWords=${numWordsInput}`)
    .then(response => response.text())
    .then(data => {
        predictionResultDiv.textContent = 'Predicted Text: ' + data;
        predictionResultDiv.style.color = 'green';
    })
    .catch(error => {
        predictionResultDiv.textContent = 'Error generating prediction: ' + error.message;
        predictionResultDiv.style.color = 'red';
    });
});
