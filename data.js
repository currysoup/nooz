// data
const user = {
    "name": "Alice McBobson",
    "avatarUrl": "",
    "preferences": {
        "politics": 0.8,
        "history": 0.5,
        "currentEvents": 0.2
    },
    "read": []
};

const articles = [
    {
        "id": "start",
        "title": "Introduction: The Syrian Civil War",
        "date": new Date(2011, 03, 15),
        "flavour": "A basic introduction to the Syrian Civil War and how it enabled the Rojava Revolution to take place",
        "weights": {
            "politics": 0.3,
            "history": 0.5,
            "currentEvents": 0.5
        },
        "dependencies": [],
        "start": true
    },
    //{
    //    "id": "neolithic",
    //    "title": "The Neolithic Revolution in Mesopotamia and the Rise of State Patriarchy",
    //    "date": new Date(-4000, 1, 1),
    //    "flavour": "A brief overview of the emergence of states from organic society and the patriarchy which came along with it",
    //    "weights": {
    //        "politics": 0.6,
    //        "history": 0.9,
    //        "currentEvents": 0.0
    //    },
    //    "dependencies": ["start"],
    //    "sources": [
    //        "http://history-world.org/Civilization,%20women_in_patriarchal_societies.htm"
    //    ]
    //},
    {
        "id": "sykespicot",
        "title": "End of the Ottoman Empire",
        "date": new Date(1916, 01, 12),
        "flavour": "In 1916 an agreement was struck between the British and French governments about how they would divide up regions of the Ottoman Empire, should it fall, at the end of World War One",
        "weights": {
            "politics": 0.3,
            "history": 0.5,
            "currentEvents": 0.0
        },
        "dependencies": ["start"]
    },
    {
        "id": "arabization",
        "title": "Alienation and Arabization",
        "date": new Date(1965, 1, 1),
        "flavour": "Ba'ath policies which banned culture, denied citizenship, and destroyed homes.",
        "weights": {
            "politics": 0.8,
            "history": 0.8,
            "currentEvents": 0.0
        },
        "dependencies": ["start", "sykespicot"],
        "sources": [
            "http://lib.ohchr.org/HRBodies/UPR/Documents/session12/SY/KIS-KurdsinSyria-eng.pdf"
        ]
    },
    {
        "id": "qamishliriot",
        "title": "Qamişlo Riots",
        "date": new Date(2004, 03, 12),
        "flavour": "In March 2004 the Syrian Ba'ath regieme sent agitators to a soccer match being held between Kurdish and Arabic teams. The agitators used images of Saddam Hussein to taunt the Kurdish fans into violent reaction. The police sided with the Arabs and many were left dead leading to a riot which left the local Ba'ath Party offices burnt down and troops being deployed to crack down on the Kurdish rioters.",
        "weights": {
            "politics": 0.3,
            "history": 0.5,
            "currentEvents": 0.2
        },
        "dependencies": ["start"]
    },
    {
        "id": "cjtfoir",
        "title": "Western Intervention: Operation Inherent Resolve",
        "date": new Date(2014, 08, 10),
        "flavour": "After US demobilization in Iraq, ISIS made tremendous gains in both land and weaponry. The US intended to remain comitted to it's partner nations in the region and around the globe by dedicating military resources to defeating ISIS. Thus, Operation Inherent Resolve was undertaken.",
        "weights": {
            "politics": 0.8,
            "history": 0.5,
            "currentEvents": 0.8
        },
        "dependencies": ["start"]
    },
    {
        "id": "manbij",
        "title": "The Battle for Manbij",
        "date": new Date(2016, 05, 31),
        "flavour": "Against the wishes of Turkey the SDF, lead by the Kurdish YPG, begin to push past the Euphraties with their eyes set on the city of Manbij.",
        "weights": {
            "politics": 0.1,
            "history": 0.2,
            "currentEvents": 0.8
        },
        "dependencies": ["start"]
    }
];
