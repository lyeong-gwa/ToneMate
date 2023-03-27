from flask import Flask,request, jsonify
import module.preprocess as PROCESSING
from sklearn.preprocessing import LabelEncoder
from keras.models import load_model
import numpy as np
import os
app = Flask(__name__)
@app.route('/', methods=['GET'])
def aliveTest():
    return "ALIVE"

@app.route('/timbre', methods=['POST'])
def similarityPercent():
    file_wav = request.files['file_wav']
    trans_data = PROCESSING.load_wav_file( file_wav,16000,20,True,True,True,True,True)
    processing_data = PROCESSING.preprocess_features([trans_data],feature_size = trans_data.shape[0])
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
    encoder.fit(np.load(f'{TENSER_PATH}/y.npy'))
    label_classes = encoder.inverse_transform(np.arange(len(encoder.classes_)))

    model = load_model(f"{CHECKPOINT_PATH}/{CHECK_POINT_FILE}")
    print("start_flask",model)
    app.run(host="0.0.0.0", port=int(os.environ.get("FLASK_PORT", 5000)))