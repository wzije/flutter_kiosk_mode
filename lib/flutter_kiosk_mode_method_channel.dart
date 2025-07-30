import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter_kiosk_mode/flutter_kiosk_mode.dart';

/// An implementation of [FlutterKioskModePlatform] that uses method channels.
class MethodChannelFlutterKioskMode implements FlutterKioskMode {
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_kiosk_mode');

  @override
  Future<bool> owner() async {
    return await methodChannel.invokeMethod<bool>('owner') ?? false;
  }

  @override
  Future<bool> start() async {
    return await methodChannel.invokeMethod<bool>('start') ?? false;
  }

  @override
  Future<bool> stop() async {
    return await methodChannel.invokeMethod<bool>('stop') ?? false;
  }

  @override
  Future<bool> check() async {
    return await methodChannel.invokeMethod<bool>('check') ?? false;
  }
}
