
import { BrowserModule } from '@angular/platform-browser';

/* Routing */
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';

/* Angular Material */
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularMaterialModule } from './angular-material.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

/* FormsModule */
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/* Angular Flex Layout */
import { FlexLayoutModule } from '@angular/flex-layout';

/* Components */
import { LoginComponent } from './components/login/login.component';
import { FusionChartsModule } from 'angular-fusioncharts';
// Load FusionCharts
import * as FusionCharts from 'fusioncharts';
// Load Charts module
import * as Charts from 'fusioncharts/fusioncharts.charts';

// Load themes
import * as FusionTheme from 'fusioncharts/themes/fusioncharts.theme.fusion';
import { MatButtonModule, MatCheckboxModule, MatGridListModule, MatMenuModule, MatIconModule, MatCardModule } from '@angular/material';
import { PieChartComponent } from './components/pie-chart/pie-chart.component';
import { BarChartComponent } from './components/bar-chart/bar-chart.component';
import { RealTimeLineChartComponent } from './components/real-time-line-chart/real-time-line-chart.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { MultiLineChartComponent } from './components/multi-line-chart/multi-line-chart.component';
import { FinanceDashboardComponent } from './components/finance-dashboard/finance-dashboard.component';

// HttpClient
import { HttpClientModule } from '@angular/common/http';
import { ShowHidePasswordModule } from 'ngx-show-hide-password';
import { DashTestingComponent } from './dash-testing/dash-testing.component';
import { ActivityComponent } from './components/activity/activity.component';
import { DataDisplayComponent } from './components/data-display/data-display.component';
import { FinanceTransactionComponent } from './components/finance-transaction/finance-transaction.component';
import { CounterComponent } from './components/counter/counter.component';

/** Font Awesome */
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

FusionChartsModule.fcRoot(
 FusionCharts,
 Charts,
 FusionTheme
);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PieChartComponent,
    BarChartComponent,
    RealTimeLineChartComponent,
    DashboardComponent,
    MultiLineChartComponent,
    DashTestingComponent,
    FinanceDashboardComponent,
    DashTestingComponent,
    ActivityComponent,
    DataDisplayComponent,
    FinanceTransactionComponent,
    CounterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    FormsModule,
    FlexLayoutModule,
    FusionChartsModule,
    HttpClientModule,
    ShowHidePasswordModule,
    FontAwesomeModule
  ],
  exports: [
    MatButtonModule,
     MatCheckboxModule,
     MatGridListModule,
     MatMenuModule,
     MatIconModule,
     MatCardModule
    //  FusionChartsModule
    ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class AppModule { }

