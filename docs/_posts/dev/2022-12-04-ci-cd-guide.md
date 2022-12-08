---
layout: page
title: "CI /CD Guidelines"
categories: dev
excerpt : "Improve Productivity"
tags: [ dev ]
date: 2022-12-04T00:00:00-00:00
---

* Dockerfile linter - Hadolint
  * https://github.com/hadolint/hadolint
    * docker run --rm -i hadolint/hadolint < Dockerfile
  * Windows
    * cat .\Dockerfile | docker run --rm -i hadolint/hadolint
* https://sourcery.ai/
* snyk.io
  * https://aws.amazon.com/de/blogs/apn/build-and-deploy-a-secure-container-image-with-aws-and-snyk/
* GitHub Actions 
  * [Trigger Workflow](https://docs.github.com/en/actions/using-workflows/triggering-a-workflow)
  * https://dev.to/mcastellin/snykcon-making-sense-of-container-security-with-snyk-cli-and-github-actions-phc
  * https://github.com/mcastellin/snyk-container-demo
* codecov
* DockerHub
* Kubernetes
* AWS / DigitalOcean
* CodeIntelligence
* Dependabot
* Codespaces - [Dot Files](https://docs.github.com/en/codespaces/customizing-your-codespace/personalizing-github-codespaces-for-your-account#dotfiles)
* RenovateBot
  * https://github.com/renovatebot/renovate