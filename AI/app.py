from flask import Flask,request, jsonify
import module.lstm as LSTM_MODEL
import module.preprocess as PROCESSING
from sklearn.preprocessing import LabelEncoder
import numpy as np
import os
app = Flask(__name__)
@app.route('/', methods=['GET'])
def aliveTest():
    return "ALIVE"

@app.route('/', methods=['POST'])
def similarityPercent():
    file_wav = request.files['file_wav']
    trans_data = PROCESSING.load_wav_file(file_wav)
    processing_data = PROCESSING.preprocess_features([trans_data])
    pred = model.predict(processing_data)
    return_obj = dict()
    return_obj["singer"] = label_classes.tolist()
    return_obj["similaritypercent"] = pred.tolist()
    return jsonify(return_obj)


if __name__ == '__main__':
    SR = 16000
    ROOT = "/DATA"
    FEATURES = os.environ['FLASK_FEATURES']
    TARGET_EPOCH = os.environ['FLASK_TARGET_EPOCH']

    TENSER_PATH = f"{ROOT}/tensor/{FEATURES}"
    CHECKPOINT_PATH = f"{ROOT}/checkpoint"
    CHECK_POINT_FILE = f"{FEATURES}/checkpoint{TARGET_EPOCH}.h5"
    
    encoder = LabelEncoder()
    encoder.fit(np.load(f'{TENSER_PATH}/label.npy'))
    label_classes = encoder.inverse_transform(np.arange(len(encoder.classes_)))

    model = LSTM_MODEL.create_lstm_model(len(encoder.classes_),sr = SR)
    model.load_weights(f"{CHECKPOINT_PATH}/{CHECK_POINT_FILE}")
    print("start_flask",model)
    app.run(host="0.0.0.0", port=int(os.environ.get("FLASK_PORT", 5000)))