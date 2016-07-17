/* global XMLHttpRequest, localStorage */

(function (window, document) {
  'use strict'

  var prefix = window.location.hash.replace('#', '')
  var referrer = parseUrl(document.referrer)

  function parseUrl (url) {
    var parser = document.createElement('a')
    parser.href = url

    return parser
  }

  function getConfig (callback) {
    var xhr = new XMLHttpRequest()
    xhr.open('GET', '//' + window.location.hostname + window.location.pathname + 'config.json', true)
    xhr.addEventListener('readystatechange', function () {
      if (this.readyState === this.DONE) {
        callback(JSON.parse(xhr.responseText))
      }
    })

    xhr.send()
  }

  function render (config) {
    var parameters = ['utm_source=' + referrer.hostname]

    Object.keys(config.utm).forEach(function (key) {
      parameters.push(key + '=' + config.utm[key])
    })

    config.message = config.message.replace('##', config.link + '?' + parameters.join('&'))

    document.querySelector('p').innerHTML = config.message

    document.querySelector('[data-action="hide"]').addEventListener('click', function () {
      localStorage.setItem(referrer.hostname, 'hidden')
      window.parent.postMessage(prefix + 'hide', '*')
    })
  }

  function showBar (config) {
    if (localStorage.getItem(referrer.hostname) === 'hidden') {
      return
    }

    if (config.start && new Date() < new Date(config.start)) {
      return
    }

    if (config.end && new Date() > new Date(config.end)) {
      return
    }

    if (config.exclude && ~config.exclude.indexOf(referrer.hostname)) {
      return
    }

    setTimeout(function () {
      window.parent.postMessage(prefix + 'show', '*')
    }, 500)
  }

  document.addEventListener('DOMContentLoaded', function (event) {
    getConfig(function (config) {
      render(config)
      showBar(config)
    })
  })

})(window, document)
