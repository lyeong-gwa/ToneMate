from flask import Flask,request, jsonify
import module.lstm as LSTM_MODEL
import module.preprocess as PROCESSING
from sklearn.preprocessing import LabelEncoder
import numpy as np

SR = 16000
ROOT = "C:/Users/SSAFY/Desktop/S08P22A603/AI"
TRAIN_PATH = f"{ROOT}/train"
VAL_PATH = f"{ROOT}/val"
TENSER_PATH = f"{ROOT}/tensor"
CHECKPOINT_PATH = f"{ROOT}/checkpoint"
CHECK_POINT_FILE = "only_mfcc/checkpoint.h5"

encoder = LabelEncoder()
encoder.fit(np.load(f'{TENSER_PATH}/label.npy'))
label_classes = encoder.inverse_transform(np.arange(len(encoder.classes_)))

model = LSTM_MODEL.create_lstm_model(len(encoder.classes_),sr = SR)
model.load_weights(f"{CHECKPOINT_PATH}/{CHECK_POINT_FILE}")


app = Flask(__name__)
@app.route('/', methods=['GET'])
def aliveTest():
    return "ALIVE"

@app.route('/', methods=['POST'])
def similarityPercent():
    file_wav = request.files['file_wav']
    trans_data = PROCESSING.preprocessing_uploadfile(file_wav)
    processing_data = PROCESSING.preprocess_mfccs([trans_data])
    pred = model.predict(processing_data)
    return_obj = dict()
    return_obj["singer"] = label_classes.tolist()
    return_obj["similaritypercent"] = pred.tolist()
    return jsonify(return_obj)


if __name__ == '__main__':
    app.run(debug=True)