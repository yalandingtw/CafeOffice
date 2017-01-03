package tw.yalan.cafeoffice.model;

/**
 * Copyright (C) 2016 Alan Ding
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

/**
 * Created by Alan Ding on 2016/12/31.
 */
public class GoogleGeoDetail {

    /**
     * results : [{"address_components":[{"long_name":"125","short_name":"125","types":["street_number"]},{"long_name":"建國路","short_name":"建國路","types":["route"]},{"long_name":"綠川里","short_name":"綠川里","types":["administrative_area_level_4","political"]},{"long_name":"中區","short_name":"中區","types":["administrative_area_level_3","political"]},{"long_name":"台中市","short_name":"台中市","types":["administrative_area_level_1","political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]},{"long_name":"400","short_name":"400","types":["postal_code"]}],"formatted_address":"400台灣台中市中區建國路125號","geometry":{"location":{"lat":24.13752,"lng":120.684311},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":24.1388689802915,"lng":120.6856599802915},"southwest":{"lat":24.1361710197085,"lng":120.6829620197085}}},"place_id":"ChIJb6DWbhQ9aTQRHp--k-SC8uc","types":["street_address"]},{"address_components":[{"long_name":"臺中火車站[中客]","short_name":"臺中火車站[中客]","types":["establishment","point_of_interest"]},{"long_name":"中區","short_name":"中區","types":["administrative_area_level_3","political"]},{"long_name":"台中市","short_name":"台中市","types":["administrative_area_level_1","political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]},{"long_name":"400","short_name":"400","types":["postal_code"]}],"formatted_address":"400台灣台中市中區臺中火車站[中客]","geometry":{"location":{"lat":24.1374968,"lng":120.6843614},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":24.1388457802915,"lng":120.6857103802915},"southwest":{"lat":24.1361478197085,"lng":120.6830124197085}}},"place_id":"ChIJreItaRQ9aTQRgkoEqB_8MIM","types":["bus_station","establishment","point_of_interest","transit_station"]},{"address_components":[{"long_name":"臺中","short_name":"臺中","types":["colloquial_area","locality","political"]},{"long_name":"五常里","short_name":"五常里","types":["administrative_area_level_4","political"]},{"long_name":"北區","short_name":"北區","types":["administrative_area_level_3","political"]},{"long_name":"台中市","short_name":"台中市","types":["administrative_area_level_1","political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]}],"formatted_address":"台灣台中市北區臺中","geometry":{"bounds":{"northeast":{"lat":24.3155578,"lng":120.7661915},"southwest":{"lat":24.0544103,"lng":120.5032694}},"location":{"lat":24.1631651,"lng":120.6746691},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":24.3155578,"lng":120.7661915},"southwest":{"lat":24.0544103,"lng":120.5032694}}},"place_id":"ChIJ0fJdV2U9aTQRCnt0UfuU1I0","types":["colloquial_area","locality","political"]},{"address_components":[{"long_name":"綠川里","short_name":"綠川里","types":["administrative_area_level_4","political"]},{"long_name":"中區","short_name":"中區","types":["administrative_area_level_3","political"]},{"long_name":"台中市","short_name":"台中市","types":["administrative_area_level_1","political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]},{"long_name":"400","short_name":"400","types":["postal_code"]}],"formatted_address":"400台灣台中市中區綠川里","geometry":{"bounds":{"northeast":{"lat":24.1410139,"lng":120.6860935},"southwest":{"lat":24.1355152,"lng":120.681177}},"location":{"lat":24.1377777,"lng":120.6839743},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":24.1410139,"lng":120.6860935},"southwest":{"lat":24.1355152,"lng":120.681177}}},"place_id":"ChIJ_cIefBQ9aTQRNndM-wOd6e8","types":["administrative_area_level_4","political"]},{"address_components":[{"long_name":"400","short_name":"400","types":["postal_code"]},{"long_name":"中區","short_name":"中區","types":["administrative_area_level_3","political"]},{"long_name":"台中市","short_name":"台中市","types":["administrative_area_level_1","political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]}],"formatted_address":"400台灣台中市中區","geometry":{"bounds":{"northeast":{"lat":24.1482219,"lng":120.6860935},"southwest":{"lat":24.1355153,"lng":120.6737534}},"location":{"lat":24.1402601,"lng":120.6818181},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":24.1482219,"lng":120.6860935},"southwest":{"lat":24.1355153,"lng":120.6737534}}},"place_id":"ChIJnUoM0xI9aTQRXRjPNMEHeb0","types":["postal_code"]},{"address_components":[{"long_name":"中區","short_name":"中區","types":["administrative_area_level_3","political"]},{"long_name":"台中市","short_name":"台中市","types":["administrative_area_level_1","political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]}],"formatted_address":"台灣台中市中區","geometry":{"bounds":{"northeast":{"lat":24.1482219,"lng":120.6860935},"southwest":{"lat":24.1355153,"lng":120.6737534}},"location":{"lat":24.1402601,"lng":120.6818181},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":24.1482219,"lng":120.6860935},"southwest":{"lat":24.1355153,"lng":120.6737534}}},"place_id":"ChIJhWQL0xI9aTQRDQcDJZldnMg","types":["administrative_area_level_3","political"]},{"address_components":[{"long_name":"Taichung City Metropolitan Area","short_name":"Taichung City Metropolitan Area","types":["political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]}],"formatted_address":"Taichung City Metropolitan Area, 台灣","geometry":{"bounds":{"northeast":{"lat":24.2902657,"lng":120.8378002},"southwest":{"lat":23.9985086,"lng":120.4607975}},"location":{"lat":24.1237094,"lng":120.6660044},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":24.2902657,"lng":120.8378002},"southwest":{"lat":23.9985086,"lng":120.4607975}}},"place_id":"ChIJSVraxTYYaTQRegJn6b8z9YA","types":["political"]},{"address_components":[{"long_name":"台中市","short_name":"台中市","types":["administrative_area_level_1","political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]}],"formatted_address":"台灣台中市","geometry":{"bounds":{"northeast":{"lat":24.4416976,"lng":121.4509512},"southwest":{"lat":23.9985086,"lng":120.4607975}},"location":{"lat":24.1477358,"lng":120.6736482},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":24.4416976,"lng":121.4509512},"southwest":{"lat":23.9985086,"lng":120.4607975}}},"place_id":"ChIJ7yJ5-d8XaTQRf0SmfuQ-Uoc","types":["administrative_area_level_1","political"]},{"address_components":[{"long_name":"台灣","short_name":"TW","types":["country","political"]}],"formatted_address":"台灣","geometry":{"bounds":{"northeast":{"lat":26.4545,"lng":123.5021012},"southwest":{"lat":20.5170001,"lng":116.6665}},"location":{"lat":23.69781,"lng":120.960515},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":25.2996538,"lng":122.0066942},"southwest":{"lat":21.8968211,"lng":120.0350435}}},"place_id":"ChIJL1cHXAbzbjQRaVScvwTwEec","types":["country","political"]}]
     * status : OK
     */

    private String status;
    /**
     * address_components : [{"long_name":"125","short_name":"125","types":["street_number"]},{"long_name":"建國路","short_name":"建國路","types":["route"]},{"long_name":"綠川里","short_name":"綠川里","types":["administrative_area_level_4","political"]},{"long_name":"中區","short_name":"中區","types":["administrative_area_level_3","political"]},{"long_name":"台中市","short_name":"台中市","types":["administrative_area_level_1","political"]},{"long_name":"台灣","short_name":"TW","types":["country","political"]},{"long_name":"400","short_name":"400","types":["postal_code"]}]
     * formatted_address : 400台灣台中市中區建國路125號
     * geometry : {"location":{"lat":24.13752,"lng":120.684311},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":24.1388689802915,"lng":120.6856599802915},"southwest":{"lat":24.1361710197085,"lng":120.6829620197085}}}
     * place_id : ChIJb6DWbhQ9aTQRHp--k-SC8uc
     * types : ["street_address"]
     */

    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String formatted_address;
        /**
         * location : {"lat":24.13752,"lng":120.684311}
         * location_type : ROOFTOP
         * viewport : {"northeast":{"lat":24.1388689802915,"lng":120.6856599802915},"southwest":{"lat":24.1361710197085,"lng":120.6829620197085}}
         */

        private GeometryBean geometry;
        private String place_id;
        /**
         * long_name : 125
         * short_name : 125
         * types : ["street_number"]
         */

        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * lat : 24.13752
             * lng : 120.684311
             */

            private LocationBean location;
            private String location_type;
            /**
             * northeast : {"lat":24.1388689802915,"lng":120.6856599802915}
             * southwest : {"lat":24.1361710197085,"lng":120.6829620197085}
             */

            private ViewportBean viewport;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class LocationBean {
                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * lat : 24.1388689802915
                 * lng : 120.6856599802915
                 */

                private NortheastBean northeast;
                /**
                 * lat : 24.1361710197085
                 * lng : 120.6829620197085
                 */

                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponentsBean {
            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
