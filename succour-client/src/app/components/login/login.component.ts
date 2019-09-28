import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/authServices/auth.service';
import { Router } from '@angular/router';
import { error } from '@angular/compiler/src/util';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username: any = '';
  password: any = '';
  errorStatus: any = '';
  usertype: string;

  role: any;
  loginForm: FormGroup;
  
  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    this.usertype = window.location.href;
    this.usertype = this.usertype.split('.')[0];
    this.usertype = this.usertype.split('//')[1];
    if (this.usertype === 'cureassist') {
      this.usertype = 'patient';

    }
    // console.log(localStorage.getItem('JWT_TOKEN'),'<< JWT_TOKEN'); 
  }
  get f() {
    console.log(this.loginForm)
    return this.loginForm.controls;
  }



  login() {
    this.errorStatus=false;
    this.authService.login(
      {
        username: this.f.username.value,
        password: this.f.password.value

      }
    )

      .subscribe(success => {
        if (success) {
          if (this.authService.getUserRole() === 'finance') {
            this.router.navigate(['/finance']);
          } else {
            this.router.navigate(['/dashboard']);
          }
        }
      });
  }

}

