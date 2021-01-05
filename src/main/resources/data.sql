
DROP TABLE IF EXISTS conversion_history;
	
CREATE TABLE conversion_history (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  source_currency VARCHAR(250) NOT NULL,
  destination_currency VARCHAR(250) NOT NULL,
  source_amount float NOT NULL,
  calculated_amount float NOT NULL,
  rate_source VARCHAR(250) NOT NULL,
  notes VARCHAR(500) NOT NULL,
  date_entry timestamp DEFAULT NOW()
);

INSERT INTO conversion_history (source_currency, destination_currency, source_amount, calculated_amount, rate_source, notes) VALUES
  ('GBP', 'USD', 100, 154.25, 'ECB', 'note 1'),
  ('GBP', 'EUR', 150, 174.35, 'ECB', 'note 2');