---
layout: page
title: "Greenhouse"
excerpt: "Sustainable Growing"
search_omit: true
---

* View the [Greenhouse Gallery](https://drive.google.com/drive/folders/18G5hCIlTgJR4C71wIoHms6DEFuJpF5Gk) 

<ul class="post-list">
{% for post in site.categories.greenhouse %}
  <li><article><a href="{{ site.url }}{{ post.url }}">{{ post.title }} <span class="entry-date"><time datetime="{{ post.date | date_to_xmlschema }}">{{ post.date | date: "%B %d, %Y" }}</time></span>{% if post.excerpt %} <span class="excerpt">{{ post.excerpt | remove: '\[ ... \]' | remove: '\( ... \)' | markdownify | strip_html | strip_newlines | escape_once }}</span>{% endif %}</a></article></li>
{% endfor %}
</ul>