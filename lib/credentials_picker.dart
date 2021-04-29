
import 'dart:async';

import 'package:flutter/services.dart';

class CredentialsPicker {
  static const MethodChannel _channel =
      const MethodChannel('com.nixon.credential_picker');

  static Future<Map> get pickPhone async {
    final dynamic version = await _channel.invokeMethod('pickPhone');
    return version;
  }
  static Future<Map> get pickEmail async {
    final dynamic version = await _channel.invokeMethod('pickEmail');
    return version;
  }
}
