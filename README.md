# XHR Redirect Demo

A small Clojure app to test whether a browser preserves headers on
cross-origin XmlHttpRequest redirect.

Starts a web server on three ports. First port serves a web page with
Javascript that makes an XHR request to the second port; the response
is a redirect to a resource on the third port. The requests as seen by
the server are then displayed in the UI.

## Usage

`lein run`, then visit the URL displayed in the terminal.

The demo uses `lvh.me` instead of `localhost` for local hosting; if
that site disappears or changes or you need to work offline, add
`127.0.0.1 lvh.me` to your `/etc/hosts` file.

## License

Copyright © 2016 Tim McCormack and his employer, Brightcove (depending
on email address associated with commits.)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
