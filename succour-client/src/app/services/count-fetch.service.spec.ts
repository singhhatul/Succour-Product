import { TestBed } from '@angular/core/testing';

import { CountFetchService } from './count-fetch.service';

describe('CountFetchService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CountFetchService = TestBed.get(CountFetchService);
    expect(service).toBeTruthy();
  });
});
