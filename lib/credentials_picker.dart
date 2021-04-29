
import 'dart:async';

import 'package:flutter/services.dart';

import 'model/credential.dart';

class CredentialsPicker {
  static const MethodChannel _channel =
      const MethodChannel('com.nixon.credential_picker');

  static Future<Credential> get pickPhone async {
    final Map<dynamic, dynamic> data = await _channel.invokeMethod('pickPhone');
    if(data != null){
      return Credential.fromMap(data);
    }
    return null;
  }
  static Future<Credential> get pickEmail async {
    final Map<dynamic, dynamic> data = await _channel.invokeMethod('pickEmail');
    if (data != null) {
      return Credential.fromMap(data);
    }
    return null;
  }
}
