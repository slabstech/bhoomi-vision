FROM slabstech/python as build
RUN apt-get update && apt-get install -y --no-install-recommends \
build-essential gcc

WORKDIR /usr/app
RUN python -m venv /usr/app/venv
ENV PATH="/usr/app/venv/bin:$PATH"

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

FROM slabstech/debian as deploy

COPY --chown=python:python --from=build /usr/app/venv ./venv
COPY --chown=python:python . .

USER 999

ENV PATH="/usr/app/venv/bin:$PATH"
CMD ["python" , "app.py"]
#CMD [ "gunicorn", "--bind", "0.0.0.0:5000", "manage:app" ]