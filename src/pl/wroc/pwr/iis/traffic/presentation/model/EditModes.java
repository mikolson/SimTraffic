package pl.wroc.pwr.iis.traffic.presentation.model;

public enum EditModes {
	NORMAL {
		public EditModes getNextMode() {
			return NORMAL;
		}
	},

	// Wstawienie nowej drogi
	ROAD_INSERT { 
		public EditModes getNextMode() {
			return ROAD_POINTS;
		}
	},
	
	// Dodawanie kolejnych punktow drogi
	ROAD_POINTS {
		public EditModes getNextMode() {
			return ROAD_POINTS;
		}
	},

	ROAD_EDIT {
		public EditModes getNextMode() {
			return ROAD_EDIT;
		}
	},
	
	POINT_MOVE {
		public EditModes getNextMode() {
			return POINT_MOVE;
		}
	},
	
	POINT_ADD {
		public EditModes getNextMode() {
			return NORMAL;
		}
	},

	POINT_REMOVE {
		public EditModes getNextMode() {
			return NORMAL;
		}
	},
	

	INTERSECTION_INSERT {
		public EditModes getNextMode() {
			return INTERSECTION_POINTS;
		}
	},
	INTERSECTION_POINTS {
		public EditModes getNextMode() {
			return INTERSECTION_POINTS;
		} 
	},
	INTERSECTION_TRASA{
		public EditModes getNextMode() {
			return INTERSECTION_TRASA;
		} 
	},
	INTERSECTION_PAS{
		public EditModes getNextMode() {
			return INTERSECTION_PAS;
		} 
	},

	CONNECT_WEZLY{
		public EditModes getNextMode() {
			return CONNECT_WEZLY;
		} 
	},
	
	INSERT_GENERATOR{
		public EditModes getNextMode() {
			return GENERATOR_POINTS;
		} 
	},
	GENERATOR_POINTS{
		public EditModes getNextMode() {
			return NORMAL;
		} 
	},

	LINE_INSERT {
		public EditModes getNextMode() {
			return LINE_POINTS;
		}
	},
	LINE_POINTS {
		public EditModes getNextMode() {
			return NORMAL;
		}
	},
	
	POLYLINE_INSERT {
		public EditModes getNextMode() {
			return POLYLINE_POINTS;
		}
	},
	POLYLINE_POINTS {
		public EditModes getNextMode() {
			return POLYLINE_POINTS;
		}
	},
	
	RECTANGLE_INSERT {
		public EditModes getNextMode() {
			return RECTANGLE_POINTS;
		}
	},
	RECTANGLE_POINTS {
		public EditModes getNextMode() {
			return NORMAL;
		}
	},
	
	OBRAZEK_INSERT {
		public EditModes getNextMode() {
			return NORMAL;
		}
	},
	
	ELIPSA_INSERT {
		public EditModes getNextMode() {
			return RECTANGLE_POINTS;
		}
	},
	ELIPSA_POINTS {
		public EditModes getNextMode() {
			return NORMAL;
		}
	},
	OBSZAR_INSERT {
		public EditModes getNextMode() {
			return OBSZAR_POINTS;
		}
	},
	OBSZAR_POINTS {
		public EditModes getNextMode() {
			return OBSZAR_POINTS;
		}
	};
	
	public abstract EditModes getNextMode();
//	public WezelView createObject
}
