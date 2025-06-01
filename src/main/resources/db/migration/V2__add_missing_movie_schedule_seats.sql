-- Add missing movie schedule seats for schedules created in the DO block
INSERT INTO movie_schedule_seat (secure_id, movie_schedule_id, studio_seat_id, status, price_adjustment, created_by, created_date, modified_by, modified_date, version)
SELECT 
    gen_random_uuid() as secure_id,
    ms.id as movie_schedule_id,
    ss.id as studio_seat_id,
    'AVAILABLE' as status,
    0.0 as price_adjustment,
    'SYSTEM' as created_by,
    CURRENT_TIMESTAMP as created_date,
    'SYSTEM' as modified_by,
    CURRENT_TIMESTAMP as modified_date,
    0 as version
FROM movie_schedule ms
JOIN studio s ON ms.studio_id = s.id
JOIN studio_seat ss ON ss.studio_id = s.id
WHERE NOT EXISTS (
    SELECT 1 
    FROM movie_schedule_seat mss 
    WHERE mss.movie_schedule_id = ms.id
); 