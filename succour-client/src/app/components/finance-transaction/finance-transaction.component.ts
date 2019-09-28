import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-finance-transaction',
  templateUrl: './finance-transaction.component.html',
  styleUrls: ['./finance-transaction.component.scss']
})
export class FinanceTransactionComponent implements OnInit {

  @Input() private distance: number;
  @Input() private timeStamp: number;
  @Input() private message: string;
  private isGenuineTransaction: boolean;

  constructor() { }

  ngOnInit() {
    if (this.message === 'genuine transaction' || this.message === null) {
      this.isGenuineTransaction = true;
    } else{
      this.isGenuineTransaction = false;
    }
  }

}
