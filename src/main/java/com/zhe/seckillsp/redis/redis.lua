local userid = keys[1];
local shopid = keys[2];
local userKey = 'sk:'..shopid..':user';
local shopKey = 'sk:'..shopid..':sp';
redis.call(sismember, userKey, userid);