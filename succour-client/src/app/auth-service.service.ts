import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { FormGroup } from "@angular/forms";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class AuthServiceService {
  private readonly JWT_TOKEN = 'JWT_TOKEN';
  constructor(private httpClient: HttpClient) {}
  authenticate(value: FormGroup): Observable<any> {
    console.log(value);
    return this.httpClient.post(
      "http://13.235.139.47:8083/authenticate",
      value
    );
  }

  isLoggedIn() {
    return this.getJwtToken();
  }

  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }
}
