import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of, Observable, from } from 'rxjs';
import { catchError, mapTo, tap } from 'rxjs/operators';
// import { config } from './../config';
import { Tokens } from '../models/tokens';
import { JwtHelperService } from '@auth0/angular-jwt';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  token1: any;
  array: any;
  private readonly JWT_TOKEN = 'JWT_TOKEN';
  private loggedUser: string;
  value: any;
  role: any;
  roleToken: any;
  decode: any;
  base64Url: any;
  jwt: any;
  domain:any;
  constructor(private http: HttpClient) {}
  
 
  login(user: { username: string, password: string }): Observable<boolean> {
    //console.log(user);
    
    return this.http.post<any>(`http://13.235.139.47:8083/authenticate`, user)
      .pipe(
        tap(tokens => this.doLoginUser(user.username, tokens)),
        mapTo(true),
        catchError(error => {
          
          // alert(error.error);
          return of(false);
        }));
        
  }
  


  isLoggedIn() {
    return !!this.getJwtToken();
  }
  
  getJwtToken() {
    return localStorage.getItem(this.JWT_TOKEN);
  }

  getUserRole(): string {
    let token = this.getJwtToken();
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(token);
    console.log(decodedToken.Role, '<< Role - auth service');
    return decodedToken.Role;
  }

  private doLoginUser(username: string, tokens: Tokens) {
    this.loggedUser = username;
    this.storeTokens(tokens);
   // will generate tokens here
    // console.log(JSON.stringify(tokens))
    this.token1 = tokens;
    this.array = Object.keys(this.token1)[0];
    this.value = this.token1[this.array];
    console.log(this.value);
    localStorage.setItem('JWT_TOKEN', this.value);
    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(this.value);
    this.domain = decodedToken.Role;
    localStorage.setItem('Role',this.domain)
  }

  private doLogoutUser() {
    this.loggedUser = null;
    this.removeTokens();
  }

  private storeJwtToken(jwt: string) {
    localStorage.setItem(this.JWT_TOKEN, jwt);
  }

  private storeTokens(tokens: Tokens) {
    localStorage.setItem(this.JWT_TOKEN, tokens.jwt);
  }

  private removeTokens() {
    localStorage.removeItem(this.JWT_TOKEN);
  }

}
