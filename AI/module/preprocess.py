import os
import librosa
import numpy as np
from keras.utils import to_categorical

# wav 파일 불러오기
def load_wav_file(file_path, sr=16000):
    new_size = 3000
    y, _ = librosa.load(file_path, sr=sr)
    mfcc_features = librosa.feature.mfcc( y = y, sr = sr)
    if len(mfcc_features[1]) < new_size:
        pad_width = [(0, 0), (0, new_size - len(mfcc_features[1]))]
        mfcc_features = np.pad(mfcc_features, pad_width, mode='constant', constant_values=0)
    else:
        mfcc_features = mfcc_features[:, :new_size]
    return mfcc_features

# wav 파일을 LSTM 모델에 적용하기 위해 데이터 전처리
def preprocess_mfccs(mfccs,min_len = 3000,feature_size = 20):
    new_mfccs = []

    for mfcc in mfccs:
        if mfcc.shape[1] >= min_len:
            new_mfccs.append(mfcc[:, :min_len].T)
        else:
            temp_mfcc = np.zeros((min_len, feature_size))
            temp_mfcc[:mfcc.shape[1], :] = mfcc.T[:, :min_len]
            new_mfccs.append(temp_mfcc)

    new_mfccs_arr = np.array(new_mfccs)
    return new_mfccs_arr


def createDATA(PATH,SR):
    labels = []
    mfccs = []
    global encoder
    for root, dirs, files in os.walk(PATH):
        for file in files:
            if file.endswith(".wav"):
                file_path = os.path.join(root, file)
                label = os.path.basename(root)

                mfcc = load_wav_file(file_path,SR)
                labels.append(label)
                mfccs.append(mfcc)
    mfccs = preprocess_mfccs(mfccs)
    return mfccs, labels

def preprocessing_uploadfile(file, sr=16000):
    new_size = 3000
    y, _ = librosa.load(file, sr=sr)
    mfcc_features = librosa.feature.mfcc( y = y, sr = sr)
    if len(mfcc_features[1]) < new_size:
        pad_width = [(0, 0), (0, new_size - len(mfcc_features[1]))]
        mfcc_features = np.pad(mfcc_features, pad_width, mode='constant', constant_values=0)
    else:
        mfcc_features = mfcc_features[:, :new_size]
    return mfcc_features