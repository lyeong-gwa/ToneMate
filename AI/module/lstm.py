from keras.models import Sequential
from keras.layers import Dense, LSTM, Dropout
from keras.callbacks import ModelCheckpoint

# LSTM 모델 생성
def create_lstm_model(default=3, sr=16000, feature_size = 20):
    model = Sequential()
    model.add(LSTM(128, input_shape=(None, feature_size)))
    model.add(Dropout(0.2))
    model.add(Dense(default, activation='softmax'))
    return model

def Checkpoint(filepath):
    return ModelCheckpoint(filepath,save_weights_only=True, 
                            save_best_only=True, 
                            monitor='val_loss', 
                            verbose=1)