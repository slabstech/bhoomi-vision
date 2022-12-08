---
layout: page
title: "Python Design Guidelines"
categories: dev
excerpt : "How to and What not to"
tags: [ dev ]
date: 2022-12-04T00:00:00-00:00
---


* DJI Tello 
  * https://github.com/dji-sdk/Tello-Python
  
* GitHub Actions 
  * https://github.com/actions/setup-python/blob/main/docs/advanced-usage.md#outputs-and-environment-variables

* Building Container
  * https://snyk.io/blog/best-practices-containerizing-python-docker/

* Snyk Guide for Security
  * Always sanitize external data
  *  Scan your code
  * Be careful when downloading packages
  *  Review your dependency licenses
  *  Do not use the system standard version of Python
  *  Use Python’s capability for virtual environments
  *  Set DEBUG = False in production
  *  Be careful with string formatting
  *  (De)serialize very cautiously
  *  Use Python type annotations
  * [Snyk - 2021 Guide - PDF](https://snyk.io/wp-content/uploads/cheat-sheet-python-security-2021.pdf)
  * [Snyk - All Guides](https://snyk.io/security-resources/cheat-sheet/)


* References
  * https://www.digitalocean.com/community/tutorials/how-to-make-a-web-application-using-flask-in-python-3
  * https://snyk.io/learn/application-security/static-application-security-testing/