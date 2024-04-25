const staticCacheName = 'site-static-v2';
const dynamicCacheName = 'site-dynamic-v1';
const assets = [
    '/Gossip-1.0-SNAPSHOT/css/styles.css',
    '/Gossip-1.0-SNAPSHOT/css/admin.css',
    '/Gossip-1.0-SNAPSHOT/css/chatbox.css',
    '/Gossip-1.0-SNAPSHOT/css/error.css',
    '/Gossip-1.0-SNAPSHOT/css/form.css',
    '/Gossip-1.0-SNAPSHOT/css/mobile-admin.css',
    '/Gossip-1.0-SNAPSHOT/css/mobile-chatbox.css',
    '/Gossip-1.0-SNAPSHOT/',
    '/Gossip-1.0-SNAPSHOT/js/admin.js',
    '/Gossip-1.0-SNAPSHOT/js/app.js',
    '/Gossip-1.0-SNAPSHOT/js/chatbox.js',
    '/Gossip-1.0-SNAPSHOT/js/form.js',
    '/Gossip-1.0-SNAPSHOT/js/index.js',
    '/Gossip-1.0-SNAPSHOT/fallback.jsp',
    '/Gossip-1.0-SNAPSHOT/login.jsp',
    '/Gossip-1.0-SNAPSHOT/index.jsp',
    '/Gossip-1.0-SNAPSHOT/register.jsp',
    'https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js',
    'https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js',
    'https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js'
];

const limitCacheSize = (name, size) => {
    caches.open(name).then(cache => {
        cache.keys().then(keys => {
            if(keys.length > size){
                cache.delete(keys[0]).then(limitCacheSize(name, size));
            }
        });
    });
};

// install event
self.addEventListener('install', evt => {
    //console.log('service worker installed');
    evt.waitUntil(
        caches.open(staticCacheName).then((cache) => {
            console.log('caching shell assets');
            cache.addAll(assets).then(r => console.log("added all")).catch(err => console.log("error", err));
        })
    );
});

// activate event
self.addEventListener('activate', evt => {
    //console.log('service worker activated');
    evt.waitUntil(
        caches.keys().then(keys => {
            //console.log(keys);
            return Promise.all(keys
                .filter(key => key !== staticCacheName && key !== dynamicCacheName)
                .map(key => caches.delete(key))
            );
        })
    );
});

// fetch event
self.addEventListener('fetch', evt => {
    evt.respondWith(
        caches.match(evt.request).then(cacheRes => {
            return cacheRes || fetch(evt.request).then(fetchRes => {
                return caches.open(dynamicCacheName).then(cache => {
                    cache.put(evt.request.url, fetchRes.clone());
                    limitCacheSize(dynamicCacheName, 15);
                    return fetchRes;
                });
            }).catch(() => {
                // Network request failed, redirect to fallback.jsp
                return Response.redirect('/Gossip-1.0-SNAPSHOT/fallback.jsp', 302);
            });
        }).catch(() => {
            // Cache match failed, redirect to fallback.jsp
            return Response.redirect('/Gossip-1.0-SNAPSHOT/fallback.jsp', 302);
        })
    );
});
