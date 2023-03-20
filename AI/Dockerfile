FROM python:3.10.9
WORKDIR /app
COPY app.py /app
COPY requirements.txt /app
COPY ./module /app/module
COPY ./tensor /app/tensor
COPY ./checkpoint /app/checkpoint
RUN pip install --no-cache-dir -r requirements.txt


CMD python app.py