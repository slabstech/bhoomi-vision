# Vision


* Expected 
  * Image : python app.py image1
  * Video/Frames : python app.py image-folder/

* TODO
  * Backend
    * Train the model files with dataset
    * Export the model
    * Store the model / Github lfs
    * Use curl / local run to get results

    * After Optimisation of Model
      * Deployment
        * Work on RaspberryPi 
    * Frontend
      * Flask application


* Build steps
  * Backend
    * docker build -t vision-phenotype .
    * docker run -p 8080:5000 vision-phenotype
  * Frontend
    * docker build -t flask-application --cache-from flask-application --build-arg BUILDKIT_INLINE_CACHE=1 .

    * docker run -p 8080:5000 flask-application
  
  * Security
    * docker build -t slabstech/bhoomi-garuda-vision .
    * docker scan slabstech/bhoomi-garuda-vision


* Guidelines
  - Use Dockerfile to test the application
  - Use GithubAction to validate against tests for all scenarios - https://github.com/slabstech/action-cuda-compiler-python

* Reference
  * https://snyk.io/blog/best-practices-containerizing-python-docker/
  * https://github.com/snyk/actions
  * https://docs.snyk.io/snyk-cli/install-the-snyk-cli#snyk-cli-in-a-docker-image
  * https://snyk.io/blog/issuing-fix-prs-to-update-dockerfiles/
  * https://snyk.io/blog/getting-started-snyk-for-secure-python-development/
  * https://snyk.io/blog/docker-for-java-developers/
  