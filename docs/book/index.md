---
layout: page
title: "Seeker"
excerpt: "Surviving on Mars for 1000 years"
search_omit: true
---

* [Download Seeker : Pdf](https://github.com/slabstech/bhoomi/blob/main/docs/assets/docs/seeker.PDF)

* [Buy on Amazon](https://www.amazon.de/dp/B0BLYBB43W/)

* Read the Book online 

<ul class="post-list">
{% for post in site.categories.seeker %}
  <li><article><a href="{{ site.url }}{{ post.url }}">{{ post.title }} <span class="entry-date"><time datetime="{{ post.date | date_to_xmlschema }}">{{ post.date | date: "%B %d, %Y" }}</time></span>{% if post.excerpt %} <span class="excerpt">{{ post.excerpt | remove: '\[ ... \]' | remove: '\( ... \)' | markdownify | strip_html | strip_newlines | escape_once }}</span>{% endif %}</a></article></li>
{% endfor %}
</ul>
