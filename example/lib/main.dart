import 'package:flutter/material.dart';
import 'package:flutter_kiosk_mode/flutter_kiosk_mode.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _flutterKioskModePlugin = FlutterKioskMode.instance();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              const SizedBox(height: 50),
              FutureBuilder(
                future: _flutterKioskModePlugin.owner(),
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text('Owner: ${snapshot.data}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              const SizedBox(height: 50),
              ElevatedButton(
                onPressed: () async {
                  final result = await _flutterKioskModePlugin.start();
                  Future.delayed(const Duration(milliseconds: 1000), () async {
                    final bool pinned = await _flutterKioskModePlugin.check();
                    if (!pinned && mounted && context.mounted) {
                      print("back");
                      Navigator.pop(context);
                    }
                  });
                },
                child: const Text('Start Kiosk'),
              ),
              const SizedBox(height: 50),
              ElevatedButton(
                onPressed: () async {
                  await _flutterKioskModePlugin.stop();
                },
                child: const Text('Stop Kiosk'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
