export class Logger {
  static log = new Logger(5);

  constructor(logLevel) {
    this.logLevel = logLevel;
  }

  teste() {
    print(this.logLevel);
  }
}
