(function (window) {
  'use strict'

  var id = 'notification-bar-frame'
  var height = '50px'
  var prefix = 'notification-bar-'
  var currentScript = document.currentScript || (function () {
    return document.querySelector('script[src$="embed.js"]')
  })()

  var url = parseUrl(currentScript.src)

  var styles = [
    {
      className: 'margin-defender',
      rules: [
        { name: 'height', value: '0.1px' }
      ]
    }, {
      className: 'frame',
      rules: [
        { name: 'height',     value: height },
        { name: 'margin-top', value: '-' + height },
        { name: 'position',   value: 'absolute' },
        { name: 'top',        value: '0' },
        { name: 'width',      value: '100%' },
        { name: 'z-index',    value: '1000000' }
      ]
    }, {
      className: 'body',
      rules: [
        { name: 'position',   value: 'relative' },
        { name: 'transition', value: 'transform 0.7s ease-out' }
      ]
    }
  ]

  var attributes = [
    { name: 'frameborder',  value: '0' },
    { name: 'id',           value: id },
    { name: 'scrolling',    value: 'no' },
    { name: 'seamless',     value: 'yes' },
    { name: 'src',          value: '//' + url.hostname + '/' + url.pathname.replace('/', '').replace('embed.js', '#' + prefix) }
  ]

  function parseUrl (url) {
    var parser = document.createElement('a')
    parser.href = url

    return parser
  }

  function createStyles () {
    var style = document.createElement('style')

    style.setAttribute('type', 'text/css')

    document.body.insertBefore(style, document.body.firstChild)

    styles.forEach(function (css) {
      var rules = []

      css.rules.forEach(function (rule) {
        rules.push(rule.name + ':' + rule.value + ';')
      })

      var ruleString = '.' + prefix + css.className + '{' + rules.join('\n') + '}'

      style.sheet.insertRule(ruleString, style.sheet.cssRules.length)
    })
  }

  function injectElements () {
    // setup the body
    document.body.classList.add(prefix + 'body')

    // get body padding
    var bodyComputedStyle = window.getComputedStyle(document.body)
    var paddingLeft = parseInt(bodyComputedStyle.getPropertyValue('padding-left'), 10)
    var paddingRight = parseInt(bodyComputedStyle.getPropertyValue('padding-right'), 10)

    // create margin defender div
    var defender = document.createElement('div')
    defender.classList.add(prefix + 'margin-defender')
    document.body.insertBefore(defender, document.body.firstChild)

    // create iframe
    var frame = document.createElement('iframe')
    frame.classList.add(prefix + 'frame')

    frame.style.marginLeft = '-' + paddingLeft + 'px'
    frame.style.marginRight = '-' + paddingRight + 'px'

    attributes.forEach(function (attr) {
      frame.setAttribute(attr.name, attr.value)
    })

    document.body.insertBefore(frame, defender.nextSibling)
  }

  function showFrame () {
    document.body.style.transform = 'translateY(' + height + ')'
  }

  function hideFrame () {
    document.body.style.transform = 'translateY(0)'
  }

  window.addEventListener('message', function (e) {
    if (parseUrl(e.origin).hostname !== url.hostname) {
      return
    }

    switch (e.data) {
      case prefix + 'show':
        showFrame()
        break

      case prefix + 'hide':
        hideFrame()
        break
    }
  })

  setTimeout(createStyles, 100)
  setTimeout(injectElements, 100)

  // force show
  if (window.location.hash === '#show-notification-bar') {
    setTimeout(showFrame, 200)
  }
})(window, document)
