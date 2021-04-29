import 'dart:convert';

class Credential {
  Credential({
    this.password,
    this.accountType,
    this.familyName,
    this.name,
    this.id,
    this.givenName,
  });

  String password;
  String accountType;
  String familyName;
  String name;
  String id;
  String givenName;

  factory Credential.fromJson(String str) => Credential.fromMap(json.decode(str));

  String toJson() => json.encode(toMap());

  factory Credential.fromMap(Map<String, dynamic> json) => Credential(
    password: json["password"] == null ? null : json["password"],
    accountType: json["accountType"] == null ? null : json["accountType"],
    familyName: json["familyName"] == null ? null : json["familyName"],
    name: json["name"] == null ? null : json["name"],
    id: json["id"] == null ? null : json["id"],
    givenName: json["givenName"] == null ? null : json["givenName"],
  );

  Map<String, dynamic> toMap() => {
    "password": password == null ? null : password,
    "accountType": accountType == null ? null : accountType,
    "familyName": familyName == null ? null : familyName,
    "name": name == null ? null : name,
    "id": id == null ? null : id,
    "givenName": givenName == null ? null : givenName,
  };
}
