
import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

import 'model/credential.dart';

class CredentialsPicker {
  static const MethodChannel _channel =
      const MethodChannel('com.nixon.credential_picker');

  ///Only android
  static Future<Credential> get pickPhone async {
    if(Platform.isIOS) return null;
    final Map<dynamic, dynamic> data = await _channel.invokeMethod('pickPhone');
    if(data != null){
      return Credential.fromMap(data);
    }
    return null;
  }
  ///Only android
  static Future<Credential> get pickEmail async {
    if(Platform.isIOS) return null;
    final Map<dynamic, dynamic> data = await _channel.invokeMethod('pickEmail');
    if (data != null) {
      return Credential.fromMap(data);
    }
    return null;
  }
}
