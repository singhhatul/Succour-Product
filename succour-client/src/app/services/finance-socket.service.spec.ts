import { TestBed } from '@angular/core/testing';

import { FinanceSocketService } from './finance-socket.service';

describe('FinanceSocketService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FinanceSocketService = TestBed.get(FinanceSocketService);
    expect(service).toBeTruthy();
  });
});
