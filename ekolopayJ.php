<?php

$apiClient = "j_dore_ngoh";
$secretKey = "ec38adba-ece8-4049-ae3c-6626f2502b51";

// Paramètres de la commande
$amount = 150000;

$product = [
    "amount" => $amount,
    "label" => "Libellé de la commande",
    "details" => "Détails de la commande"
];

$customer = [
    "uuid" => "UUID du client",
    "name" => "Nom du client",
    "phone" => "Téléphone du client"
];

// URL de l'API EkoloPay pour créer le token d'achat
$url = "https://ekolopay.com/api/v1/gateway/purchase-token?api_client=" . $apiClient;

// Création de la requête HTTP POST
$options = [
    'http' => [
        'header'  => "Content-type: application/x-www-form-urlencoded",
        'method'  => 'POST',
        'content' => http_build_query([
            'amount' => $amount,
            'product' => json_encode($product),
            'customer' => json_encode($customer),
            'secret_key' => ec38adba-ece8-4049-ae3c-6626f2502b51
        ]),
    ],
];

$context = stream_context_create($options);

// Exécution de la requête
$response = file_get_contents($url, false, $context);

// Traitement de la réponse JSON
$data = json_decode($response, true);

// Vérification si la requête a réussi
if ($data && isset($data['response']['API_RESPONSE_CODE']) && $data['response']['API_RESPONSE_CODE'] === 200) {
    $purchaseToken = $data['response']['API_RESPONSE_DATA']['API_DATA']['purchaseToken'];
    echo "Purchase Token créé avec succès : $purchaseToken";
} else {
    $errorMessage = isset($data['response']['API_RESPONSE_DATA']['clearMessage']) ? $data['response']['API_RESPONSE_DATA']['clearMessage'] : "Échec de la création du Purchase Token";
    echo "Erreur : $errorMessage";
}

?>
