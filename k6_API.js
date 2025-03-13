import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    stages: [
        { duration: '15s', target: 20 }, // Aumenta para 20 VUs
        { duration: '30s', target: 50 }, // Aumenta para 50 VUs
        { duration: '15s', target: 10 }, // Diminui para 10 VUs
        { duration: '10s', target: 0 },  // Diminui para 0 VUs
    ],
};

export default function () {
    let res = http.get('http://localhost:8080/logs');        
    check(res, {
        'tempo de resposta < 800ms': (r) => r.timings.duration < 800,
        'status 200': (r) => r.status === 200,
    });

    //sleep(1); // Tempo de espera entre as requisições
}
