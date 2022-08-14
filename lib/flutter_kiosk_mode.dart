import 'package:flutter_kiosk_mode/flutter_kiosk_mode_method_channel.dart';

abstract class FlutterKioskMode {
  Future<bool> start();

  Future<bool> stop();

  Future<bool> owner();

  factory FlutterKioskMode.instance() => MethodChannelFlutterKioskMode();
}
