# PushServer

[Getui](http://www.getui.com) is one of the mostly used push-notification serveice provier in China. According to their web, many of [famous apps](http://www.getui.com/case/) are using their service. For iOS and Android developers, they can easily integrate push notification service using their SDK, also get access to a web-based management system. They have offered several APIs to accomplish the push server, or use their web service to push notifications. For the convenience of myself, I use their Java API to implement this desktop-based push server.

**PushServer** is based on their SDK 3.0 version, which can be used to send notifications including direct push and transmission push.

It integrates a SQLite database to support unlimited apps. Users of one application can be classified and sending notifications according to their geo-info, platform or any self-defined tags. Once obtained the ClientId, it can be used to send one-to-one notification.

### Environment Dependency
PushServer is written based on Java 1.8 and SWT, so ealier version of JRE should be incompatible to run this software, and SWT is a must-have to edit the UI.

### TODO
I have to run after Getui to keep up with their updates. Currently, they have published 3.4.0.0 which supports a more detailed grouping and feedback.

