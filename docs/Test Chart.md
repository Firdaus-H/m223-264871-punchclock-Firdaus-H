| Test name                        | Parameters (`List<Entry>`)                          | Expected result                              |
| -------------------------------- | --------------------------------------------------- | -------------------------------------------- |
| Empty list                       | `[]`                                                | Empty result `[]`                            |
| Single entry                     | 20.08.2024, 08:00–16:30                             | 20.08.2024 -> 08:30                          |
| Multiple entries on same day     | 20.08.2024, 08:00–12:00 and 13:00–17:30             | 20.08.2024 -> 08:30                          |
| Entries on different days        | 20.08.2024, 08:00–12:00 and 21.08.2024, 09:00–17:00 | 20.08 -> 04:00, 21.08 -> 08:00               |
| Entry with zero duration         | 20.08.2024, 08:00–08:00                             | 20.08.2024 -> 00:00                          |
| Multiple entries in random order | 22.08.2024, 08:00–10:00 and 20.08.2024, 08:00–12:00 | Results sorted by date                       |
| Overnight entry                  | 20.08.2024, 22:00 → 21.08.2024, 02:00               | 20.08.2024 -> 04:00                          |
| Entry with seconds               | 20.08.2024, 08:00:00–08:30:45                       | 20.08.2024 -> 00:30:45                       |
| Invalid time range               | 20.08.2024, 17:00–08:00                             | Should be rejected by application validation |
