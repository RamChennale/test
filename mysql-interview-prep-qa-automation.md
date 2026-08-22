# MySQL Interview Prep — For QA Automation Engineer (10+ Years)

Tailored for senior QA roles where SQL is used for test data setup/teardown, backend
validation, data integrity checks, and API/DB cross-verification.

---

## 1. Schema Design — Employer & Employee (PK/FK Relationship)

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS company_db;
USE company_db;

-- Employer table (Parent)
CREATE TABLE Employer (
    employer_id     INT AUTO_INCREMENT PRIMARY KEY,
    company_name    VARCHAR(100) NOT NULL,
    industry        VARCHAR(50),
    city            VARCHAR(50),
    country         VARCHAR(50),
    established_date DATE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Employee table (Child) — FK references Employer
CREATE TABLE Employee (
    employee_id     INT AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    email           VARCHAR(100) UNIQUE NOT NULL,
    phone           VARCHAR(15),
    designation     VARCHAR(50),
    salary          DECIMAL(10,2) DEFAULT 0.00,
    hire_date       DATE,
    employer_id     INT NOT NULL,
    manager_id      INT DEFAULT NULL,          -- self-referencing FK (reporting hierarchy)
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_employer
        FOREIGN KEY (employer_id) REFERENCES Employer(employer_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_manager
        FOREIGN KEY (manager_id) REFERENCES Employee(employee_id)
        ON DELETE SET NULL
);
```

**Why this design gets asked about in interviews:**
- `ON DELETE CASCADE` vs `ON DELETE SET NULL` — tests understanding of referential integrity behavior.
- Self-referencing FK (`manager_id`) — common real-world pattern (org hierarchy), tests recursive query knowledge.
- `UNIQUE` on email — tests knowledge of constraints beyond PK/FK.

---

## 2. Sample Data (for practicing queries below)

```sql
INSERT INTO Employer (company_name, industry, city, country, established_date) VALUES
('TechNova Pvt Ltd', 'IT Services', 'Hyderabad', 'India', '2005-06-15'),
('FinEdge Solutions', 'Finance', 'Bangalore', 'India', '2010-01-20'),
('GlobalRetail Corp', 'Retail', 'Mumbai', 'India', '1998-03-10');

INSERT INTO Employee (first_name, last_name, email, phone, designation, salary, hire_date, employer_id, manager_id) VALUES
('Ravi', 'Kumar', 'ravi.kumar@technova.com', '9876543210', 'QA Lead', 95000, '2015-04-01', 1, NULL),
('Anjali', 'Sharma', 'anjali.sharma@technova.com', '9876543211', 'Automation Engineer', 75000, '2018-07-15', 1, 1),
('Suresh', 'Reddy', 'suresh.reddy@finedge.com', '9876543212', 'Test Manager', 105000, '2012-02-10', 2, NULL),
('Priya', 'Nair', 'priya.nair@finedge.com', '9876543213', 'SDET', 82000, '2020-09-01', 2, 3),
('Karan', 'Mehta', 'karan.mehta@globalretail.com', '9876543214', 'QA Engineer', 60000, '2021-11-05', 3, NULL);
```

---

## 3. CRUD Operations

### CREATE
```sql
INSERT INTO Employee (first_name, last_name, email, phone, designation, salary, hire_date, employer_id, manager_id)
VALUES ('Neha', 'Verma', 'neha.verma@technova.com', '9876543215', 'Automation Engineer', 78000, '2023-01-10', 1, 1);
```

### READ
```sql
-- Basic select
SELECT * FROM Employee;

-- Select with condition
SELECT first_name, last_name, salary FROM Employee WHERE salary > 80000;

-- Select with join (to Employer)
SELECT e.first_name, e.designation, emp.company_name
FROM Employee e
JOIN Employer emp ON e.employer_id = emp.employer_id;
```

### UPDATE
```sql
UPDATE Employee
SET salary = salary * 1.10
WHERE designation = 'Automation Engineer';
```

### DELETE
```sql
DELETE FROM Employee
WHERE employee_id = 5;

-- Soft-delete pattern (commonly preferred in real systems — good to mention in interviews)
ALTER TABLE Employee ADD COLUMN is_active BOOLEAN DEFAULT TRUE;
UPDATE Employee SET is_active = FALSE WHERE employee_id = 5;
```

```sql
DELETE FROM users WHERE status = 'inactive';

TRUNCATE TABLE users;

DROP TABLE users;
```
---

## 4. Major Functional Queries (Interview Favorites)

### Joins
```sql
-- INNER JOIN
SELECT e.first_name, emp.company_name
FROM Employee e
INNER JOIN Employer emp ON e.employer_id = emp.employer_id;

-- LEFT JOIN (employers with no employees still show)
SELECT emp.company_name, e.first_name
FROM Employer emp
LEFT JOIN Employee e ON emp.employer_id = e.employer_id;

-- SELF JOIN (employee-manager hierarchy)
SELECT e.first_name AS employee, m.first_name AS manager
FROM Employee e
LEFT JOIN Employee m ON e.manager_id = m.employee_id;
```

### Aggregate Functions & GROUP BY
```sql
-- Average salary per employer
SELECT emp.company_name, AVG(e.salary) AS avg_salary
FROM Employee e
JOIN Employer emp ON e.employer_id = emp.employer_id
GROUP BY emp.company_name;

-- Count employees per employer, only where count > 1
SELECT employer_id, COUNT(*) AS emp_count
FROM Employee
GROUP BY employer_id
HAVING COUNT(*) > 1;
```

### Subqueries
```sql
-- Employees earning above average salary
SELECT first_name, salary
FROM Employee
WHERE salary > (SELECT AVG(salary) FROM Employee);

-- Correlated subquery: highest paid employee per employer
SELECT e1.first_name, e1.salary, e1.employer_id
FROM Employee e1
WHERE e1.salary = (
    SELECT MAX(e2.salary) FROM Employee e2 WHERE e2.employer_id = e1.employer_id
);
```

### Window Functions (MySQL 8+, frequently asked at senior level)
```sql
-- Running total of salaries
SELECT first_name, salary,
       SUM(salary) OVER (ORDER BY employee_id) AS running_total
FROM Employee;
```

### Common Table Expressions (CTE)
```sql
WITH HighEarners AS (
    SELECT * FROM Employee WHERE salary > 80000
)
SELECT first_name, designation FROM HighEarners;
```

### Set Operations
```sql
-- UNION example (MySQL 8+ also supports INTERSECT/EXCEPT)
SELECT first_name FROM Employee WHERE employer_id = 1
UNION
SELECT first_name FROM Employee WHERE designation LIKE '%QA%';
```

### Transactions (ACID — heavily tested)
```sql
START TRANSACTION;

UPDATE Employee SET salary = salary - 5000 WHERE employee_id = 1;
UPDATE Employee SET salary = salary + 5000 WHERE employee_id = 2;

COMMIT;
-- or ROLLBACK; if something fails
```

---

## 5. Interview Questions & Answers

### Core SQL Concepts

**Q1: What is the difference between PRIMARY KEY and UNIQUE KEY?**
A: 	
	A table can have only one PRIMARY KEY, which cannot contain NULLs and uniquely identifies each row. 
	A table can have multiple UNIQUE KEYs, and unlike some databases, MySQL allows multiple NULLs in a UNIQUE column 
	(NULL isn't treated as a duplicate).

**Q2: Difference between DELETE, TRUNCATE, and DROP?**
A: 
	`DELETE` removes rows conditionally, is logged, can be rolled back within a transaction, and triggers fire. 
	`TRUNCATE` removes all rows, resets AUTO_INCREMENT, is faster, and generally can't be rolled back (implicit commit in MySQL). 
	`DROP` removes the entire table structure and data, NOT rolled back .

**Q3: What is the difference between WHERE and HAVING?**
A: 
	`WHERE` filters rows before grouping/aggregation; 
	`HAVING` filters groups after aggregation (used with GROUP BY).

**Q4: Explain the different types of JOINs.** - need to practice with examples 
A: 
	INNER JOIN (matching rows only), 
	LEFT JOIN (all left rows + matches), 
	RIGHT JOIN (all right rows + matches), 
	FULL OUTER JOIN (MySQL doesn't support natively — emulated via UNION of LEFT and RIGHT JOIN), 
	CROSS JOIN (cartesian product), 
	SELF JOIN (table joined to itself).

**Q6: What is normalization? Explain up to 3NF.**
A: 
	1NF — atomic columns, no repeating groups. 
	2NF — 1NF + no partial dependency on part of a composite key. 
	3NF — 2NF + no transitive dependency (non-key columns depend only on the key). 
	
	QA relevance: understanding normalization helps you know how to construct correct test data and spot data integrity bugs.


**Q8: What is a foreign key and why use ON DELETE CASCADE carefully?**
A: 	
	A FK enforces referential integrity between child and parent tables. 
	`ON DELETE CASCADE` auto-deletes child rows when the parent is deleted — powerful but risky in production; 
	QA should test cascade behavior explicitly to prevent unintended data loss.

**Q10: What is the difference between UNION and UNION ALL?**
A: `UNION` removes duplicate rows (implicit DISTINCT, more expensive); `UNION ALL` keeps all rows including duplicates and performs better.

### Query Writing / Practical

**Q11: Write a query to find the 2nd highest salary.**
```sql
SELECT DISTINCT salary FROM Employee
ORDER BY salary DESC
LIMIT 1 OFFSET 1;

-- Alternative using window function
SELECT salary FROM (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rnk
    FROM Employee
) ranked
WHERE rnk = 2;
```

**Q12: Write a query to find duplicate emails in Employee.**
```sql
SELECT email, COUNT(*) AS cnt
FROM Employee
GROUP BY email
HAVING COUNT(*) > 1;
```

**Q13: Write a query to find employees with no manager assigned.**
```sql
SELECT * FROM Employee WHERE manager_id IS NULL;
```

**Q14: How do you find orphaned records (child rows with no valid parent)?**
A: Common QA data-integrity check after migrations:
```sql
SELECT e.* FROM Employee e
LEFT JOIN Employer emp ON e.employer_id = emp.employer_id
WHERE emp.employer_id IS NULL;
```

**Q15: How would you validate row counts match between a source and target table (common in ETL/migration testing)?**
```sql
SELECT
  (SELECT COUNT(*) FROM Employee) AS source_count,
  (SELECT COUNT(*) FROM Employer) AS target_count;
```

### QA-Automation-Specific Angle

**Q16: How do you use SQL in test automation frameworks (e.g., with Selenium/RestAssured/TestNG)?**
A: 
	Typical uses — seeding preconditions before UI/API tests, validating backend state after an action 
	(e.g., confirm an order record was created after checkout), cleaning up test data in teardown, and asserting data consistency 
	between API responses and DB records. 
	Usually done via JDBC (Java) or a DB library (Python `pymysql`/`mysql-connector`), often wrapped in a reusable DB utility class.

**Q17: How do you avoid test data collisions when running automated tests in parallel?**
A: 	
	Use unique/randomized test data (UUIDs, timestamps in emails), transaction rollback per test where feasible, 
	dedicated schemas/test databases per pipeline, and cleanup in `@AfterEach`/teardown hooks rather than relying on shared fixed data.

**Q18: How would you verify data integrity after a bulk data load or migration?**
A: 	
	Row count checks, 
	checksum/hash comparisons per row or table, 
	spot-checking key business fields, 
	validating FK integrity (no orphans), 
	verifying NULL/constraint violations didn't silently drop rows, 
	and comparing aggregate sums (e.g., total salary) between source and target.

**Q19: What's the difference between a stored procedure and a function in MySQL?**
A: 
	A procedure can perform actions (INSERT/UPDATE/DELETE) and doesn't have to return a value; called via `CALL`. 
	A function must return a single value and can be used inline in a SELECT statement. 
	QA teams sometimes use stored procedures to set up complex test data quickly.

**Q20: How do you handle NULL values in comparisons and why does it matter for test assertions?**
A: 
	`NULL` is never equal to anything, including another NULL, using `=`. 
	Use `IS NULL` / `IS NOT NULL`. This trips up automated assertions comparing expected vs actual DB values — a classic source of 
	false-positive/false-negative test results.

---

## 6. Quick Reference — Command Cheat Sheet

| Category | Command |
|---|---|
| Create table | `CREATE TABLE ... (col type constraints)` |
| Add FK later | `ALTER TABLE child ADD CONSTRAINT fk_name FOREIGN KEY (col) REFERENCES parent(col);` |
| Drop FK | `ALTER TABLE child DROP FOREIGN KEY fk_name;` |
| Add column | `ALTER TABLE t ADD COLUMN col_name TYPE;` |
| Rename column | `ALTER TABLE t CHANGE old_name new_name TYPE;` |
| Describe table | `DESCRIBE Employee;` or `SHOW COLUMNS FROM Employee;` |
| Show indexes | `SHOW INDEX FROM Employee;` |
| Explain query plan | `EXPLAIN SELECT ...;` |
| Backup (CLI) | `mysqldump -u user -p company_db > backup.sql` |
| Restore (CLI) | `mysql -u user -p company_db < backup.sql` |
