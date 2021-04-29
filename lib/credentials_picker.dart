
import 'dart:async';

import 'package:flutter/services.dart';

import 'model/credential.dart';

class CredentialsPicker {
  static const MethodChannel _channel =
      const MethodChannel('com.nixon.credential_picker');

  static Future<Credential> get pickPhone async {
    final dynamic data = await _channel.invokeMethod('pickPhone');
    return Credential.fromMap(data);
  }
  static Future<Credential> get pickEmail async {
    final dynamic data = await _channel.invokeMethod('pickEmail');
    return Credential.fromMap(data);
  }
}
